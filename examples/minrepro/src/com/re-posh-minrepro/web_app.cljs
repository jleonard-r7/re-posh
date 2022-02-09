(ns ^:figwheel-hooks com.re-posh-minrepro.web-app
  (:require
   [com.re-posh-minrepro.state :as state]
   [com.re-posh-minrepro.db :as db]
   [datascript.core :as d]
   [goog.dom :as gdom]
   [goog.string :refer [format]]
   [reagent.core :as r]
   [reagent.dom :as rdom]
   [re-frame.core :as rf]))

(defn- app []
  (let [i (r/atom 0)]
    (fn []
      [:div
       (str "current timerange from subscribe: " @(rf/subscribe [::state/timerange]))
       [:br]
       [:button {:on-click (fn [_]
                             (swap! i inc)
                             (rf/dispatch-sync [::state/set-timerange {:timerange (str @i "w")}]))}
        "Click Me"]
       [:br]
       [:br]
       (str "current timerange from db(" @i "): "
            (d/q '[:find ?t . :where [:timerange :timerange/timerange ?t]] @db/conn))])))

(defn- mount-app-element []
  (when-let [el (gdom/getElement "app")]
    (rdom/render [app] el)))

(defn init! []
  (state/init!)
  (js/console.debug "dispatching core-state/initialize-db...")
  (rf/dispatch-sync [::state/initialize-db])
  (js/console.debug "mounting app element...")
  (mount-app-element))

(defn ^:after-load on-reload []
  (init!))

(defonce init-block
  (do
    (init!)))
