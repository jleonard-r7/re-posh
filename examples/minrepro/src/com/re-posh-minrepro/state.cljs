(ns com.re-posh-minrepro.state
  (:require
   [com.re-posh-minrepro.db :as db]
   [re-frame.core :as rf]
   [re-posh.core :as rp]))

(defn init! []

  ;; EVENTS

  (rp/reg-event-ds
   ::initialize-db
   (fn [_ _]
     db/initial-db))

  (rp/reg-event-ds
   ::set-timerange
   [rf/trim-v]
   (fn [_ [timerange]]
     (js/console.log "setting timerange to: " timerange)
     [[:db/add :timerange :timerange/timerange timerange]]))

  ;; SUBSCRIPTIONS

  (rp/reg-query-sub
   ::timerange
   '[:find ?t . :where [:timerange :timerange/timerange ?t]]))
