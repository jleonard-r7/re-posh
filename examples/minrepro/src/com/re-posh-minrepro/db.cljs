(ns com.re-posh-minrepro.db
  (:require
   [datascript.core :as datascript]
   [re-posh.core :as re-posh]))

(def initial-db
  [{:db/ident :timerange
    :timerange/timerange {:timerange "24h"}}])

(def conn (datascript/create-conn))
(re-posh/connect! conn)
