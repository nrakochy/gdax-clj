(ns gdax-clj.api
  (:require [gdax-clj.http :as http]))

(defn- make-http-request [m]
  (-> (assoc m :endpoint-type :rest)
      (http/gdax-ajax)))

(defn get [m]
  (-> (assoc m :method "GET")
      (make-http-request)))

(defn post [m]
  (-> (assoc m :method "POST")
       (make-http-request)))

(defn delete [m]
  (-> (assoc m :method "DELETE")
      (make-http-request)))
