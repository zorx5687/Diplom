import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import data from "./data.js";
import mitt from "mitt";


const emitter = mitt();
const app = createApp(App, {
    data: function () {
        return data
    }
}).use(router);


app.config.globalProperties.emitter = emitter;
app.mount('#app')
