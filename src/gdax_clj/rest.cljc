(ns gdax-clj.rest
  (:require [gdax-clj.http :as http]))

(def rest-domain "https://api.gdax.com")

(def routes {"GET" {:account {:route "/accounts/{{account-id}}"}}
             "POST" {}
             "DELETE" {}})

(defn- make-http-request [{:keys [method resource]
                           :as   request}]
  (-> (merge (get-in routes [method resource]) request)
      (assoc :domain rest-domain)
      (http/gdax-ajax)))

(defn- get [request]
  (-> (assoc request :method "GET")
      (make-http-request)))

(defn- post [request]
  (-> (assoc request :method "POST")
       (make-http-request)))

(defn- delete [request]
  (-> (assoc request :method "DELETE")
      (make-http-request)))

(defn get-account [{:keys [account-id] :as m}]
  (get (assoc m :resource :account)))

(defn show-routes [] routes)

(get-account {:account-id 1})
