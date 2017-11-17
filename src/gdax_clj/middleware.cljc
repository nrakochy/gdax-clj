(ns gdax-clj.middleware
  (:require         [clojure.spec.alpha :as s]
                    [gdax-clj.auth :as auth]
                    [selmer.parser :as parser]))

(def url-resource {:rest "https://api.gdax.com"
                   :fix  "https://fix.gdax.com:4198"
                   :ws    "wss://ws-feed.gdax.com"})

(defn response-handler
  "Hand ajax response back to caller"
  [[ok response]] response)

(defn set-handler [request]
  (assoc request :handler response-handler))

(defn set-auth-headers
  "Adds gdax-specific headers to the request.
  This must be the last interceptor run for the message signing to work correctly"
  [{:keys [headers]
    :or   {headers {}}
    :as   request}]
  (->> (auth/get-auth-map request)
       (merge headers)
       (assoc request :headers)))

(defn set-uri [{:keys [domain route method resource] :as request}]
  (let [{:keys [route]} (get-in routes [method resource])
        relative-path   (parser/render route request)
        domain          (get url-resource endpoint-type)]
    (-> request
        (assoc :path relative-path)
        (assoc :uri (str domain relative-path)))))

(def default-required-keys [::api-key ::secret ::password])
(s/fdef ::configure-request
        :args (s/cat :request map?)
        :ret map?)
         ;; :fn (fn [ctx]
         ;;         (= (into #{} (-> ctx :args :ks)))))
         ;;      (into #{} (-> ctx :ret keys))))

(def req {:method "GET"
          :resource :account
          :endpoint-type :rest
          :account-id 1})
