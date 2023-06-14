<template>
    <Header :user="user"/>
    <Middle :clicked="clicked" :post="post" :categories="categories" :posts="posts" :currentUser="currentUser"
            :user="user"
            :items="data.items" :item="data.item"/>
    <Footer/>
</template>

<script>
// import Middle from "@/components/Middle.vue";
import data from "@/data";
import Footer from "@/components2/Footer.vue";
import Header from "@/components2/Header.vue";
import Middle from "@/components2/Middle.vue";
import axios from "axios";
// import Header from "@/components/Header.vue";
// import Footer from "@/components/Footer.vue";

export default {
    components: {Footer, Header, Middle},
    computed: {
        data() {
            return data
        }
    },
    data: function () {
        return {
            user: null,
            currentUser: null,
            categories: null,
            posts: null,
            post: null,
            activeCat: null,
            clicked: ""
        }
    },
    beforeCreate() {
        this.emitter.on('onShowUser', (id) => {
            this.clicked = "";
            let resuset = 'http://localhost:8080/api/v1/user/' + id;
            axios.get(resuset)
                .then(response => {
                    this.currentUser = response.data;
                    this.$router.push(
                        {
                            name: 'profile',
                            params: {
                                id: id
                            },
                        });
                });
        });
        this.emitter.on('onShowPost', (id) => {
            let resuset = 'http://localhost:8080/api/v1/post/' + id;
            this.clicked = "";
            alert(resuset)
            axios.get(resuset)
                .then(response => {
                    this.post = response.data;
                    this.$router.push(
                        {
                            name: 'post-page',
                            params: {
                                id: id
                            },
                        });
                });
        });
        this.emitter.on('onLogin', (user) => {
            this.user = user;
            localStorage.setItem('user', JSON.stringify(user));
        });
        this.emitter.on('clearActive', () => {
            this.clicked = "";
        })
        this.emitter.on('onAddPost', () => {
            this.clicked = "";
            axios.get("http://localhost:8080/api/v1/post").then(response => {
                console.log("REPOSTNE" + JSON.stringify(response.data.posts));
                this.posts = response.data.posts;
            });
        });
        this.emitter.on('onShowCat', (name) => {
            if (this.clicked === name) {
                this.clicked = ""
            } else {
                this.clicked = name;
            }
            axios.get("http://localhost:8080/api/v1/post?category=" + this.clicked).then(response => {
                console.log("REPOSTNE" + JSON.stringify(response.data.posts));
                this.posts = response.data.posts;
                this.$router.push('/index')
            });
        });
        this.emitter.on('onDropClicked', () => {
            this.clicked = "";
            this.$router.push('/index');
        });
        this.emitter.on('onLogout', () => {
            this.user = null;
            this.clicked = "";
            localStorage.removeItem('user');
            this.$router.push('/index');
        });
        this.emitter.on('onSearch', (search) => {
            axios.get("http://localhost:8080/api/v1/post/contains?value=" + search).then(response => {
                this.posts = response.data.posts;
                this.$router.push('/index')
            });
        })
    },
    beforeMount() {
        if (localStorage.getItem('user')) {
            this.user = JSON.parse(localStorage.user);
        }
        axios.get("http://localhost:8080/api/v1/post/categories").then(response => {
            console.log("REPOSTNE" + response);
            this.categories = response.data.categories;
        });
        axios.get("http://localhost:8080/api/v1/post").then(response => {
            console.log("REPOSTNE" + JSON.stringify(response.data.posts));
            this.posts = response.data.posts;
        });
    }
    ,

}
</script>


<style scoped>

</style>
