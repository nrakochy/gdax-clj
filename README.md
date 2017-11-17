# gdax-clj
Clojure(script) wrapper for [gdax-api](https://docs.gdax.com/)

## Usage
WIP at the moment - not even alpha -- at least until messages can be signed in `clj` & `cljs`

#### Preqs
* You must have a `gdax` account with an `api-key`, `passphrase`, and `secret` for auth

#### API
* Data all the way down. Pass in a map, return a `response` map

````
(:require [gdax-clj.api :as api])

(api/get {:resource :account :account-id id-num-here})
````

## License

Copyright © 2017 Nick Rakochy 

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
