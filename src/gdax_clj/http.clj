(ns gdax-clj.http
  (:require [gdax-clj.middleware :as mid] 
            [ajax.core :as ajax]))

(def build-uri
  (ajax/to-interceptor {:name "Create uri from path"
                        :request mid/set-uri}))
(def add-handler
  (ajax/to-interceptor {:name "Return response handler"
                        :request mid/set-handler}))
(def auth-headers
  (ajax/to-interceptor {:name   "Decorate headers with auth"
                        :request mid/set-auth-headers}))
(def default-opts
  {:timeout         5000
   :format          (ajax/json-request-format)
   :interceptors    [ajax/default-interceptors add-handler build-uri auth-headers]
   :response-format (ajax/json-response-format {:keywords? true})})

;;- to spec -- required keys: path, handler
;;- required output keys from ajax-cljs api
(defn set-ajax-params [m]
    (merge m default-opts))

(defn gdax-ajax [m]
  (-> m set-ajax-params)) ;;ajax/ajax-request))

(def req {:method "GET"
          :resource :account
          :endpoint-type :rest
          :account-id 1})

(set-ajax-params req)
