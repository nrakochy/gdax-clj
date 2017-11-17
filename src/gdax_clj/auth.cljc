(ns gdax-clj.auth)

(defn getTimestamp[]
  #?(:clj  (System/currentTimeMillis)
     :cljs (.getTime (js/Date.))))

(defn prehash-str
  [timestamp {:keys [method path body]}]
   (str timestamp method path body))

;;https://anmonteiro.com/2017/03/requiring-node-js-modules-from-clojurescript-namespaces/
(defn decode-secret
  [{:keys [secret]}])
  ;; (Buffer. secret "base-64")

(defn generate-hmac [request])
  ;;(.createHmac 'sha256' (decode-secret request))

(defn signed-request [token request])
;; (let [hmac (generate-hmac request)])
;;  (.digest (.update hmac token))

(defn get-auth-map
  [{:keys [params password api-key]
    :or   {params {}}
    :as   request}]
  (let [timestamp (getTimestamp)
        token (prehash-str timestamp request)
        signed (signed-request token request)]
    {:cb-access-sign signed
      :cb-access-timestamp timestamp
      :cb-access-passphrase password
      :cb-access-key api-key}))
