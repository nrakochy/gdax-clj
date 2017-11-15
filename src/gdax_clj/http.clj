(ns gdax-clj.http
  (:require [clojure.set :as set]
            [ajax.core :as ajax]))

(defn sign-token [params])

(defn response-handler [[ok response]] response)

(defn set-handler [request]
  (update request :handler response-handler))

(defn set-auth-headers
  "Adds gdax-specific headers to the request.
  This must be the last interceptor run for the message signing to work correctly"
  [{:keys [params] :as request}]
  (let [token   (sign-token)
        headers {:Accept        "application/json"
                 :CB-ACCESS-SIGN ""
                 :CB-ACCESS-TIMESTAMP ""
                 :CB-ACCESS-PASSPHRASE ""
                 :CB-ACCESS-KEY (str "Token " token)}]
        (update request :headers #(merge % headers))))

(def add-handler 
  (ajax/to-interceptor {:name "Return response handler"
			:request set-handler}))

(def auth-headers
  (ajax/to-interceptor {:name   "Decorate headers with auth"
                        :request set-auth-headers}))
(def default-opts
  {:timeout         5000
   :format          (ajax/json-request-format)
   :interceptors    [ajax/default-interceptors auth-headers]
   :response-format (ajax/json-response-format {:keywords? true})})

;;- to spec -- required keys: uri, handler
;;- required output keys from ajax-cljs api
(defn- set-ajax-params [{:as m
                         :keys [interceptors]
                         :or {interceptors []}}]
  (let [interceptor-map {:interceptors (into [] (concat interceptors (:interceptors default-opts)))}]
    (merge-with into interceptor-map m default-opts)))

(defn- gdax-ajax [m]
  (-> m set-ajax-params ajax/ajax-request))

;;- Additionally, post request will need params key even if empty
(defn gdax-post [m]
  (gdax-ajax (assoc m :method :post)))

;;- to spec -- required keys: uri, handler
;;- required output keys from ajax-cljs api
(defn gdax-get [m]
  (gdax-ajax (assoc m :method :get)))