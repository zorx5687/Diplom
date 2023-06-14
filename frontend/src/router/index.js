import {createRouter, createWebHistory} from "vue-router";
import EnterPage from "@/components2/page/EnterPage.vue";
import RegisterPage from "@/components2/page/RegisterPage.vue";
import ProfilePage from "@/components2/page/ProfilePage.vue";
import AddPost from "@/components2/page/AddPost.vue";
import Posts from "@/components2/page/Posts.vue";
import PostPage from "@/components2/page/PostPage.vue";
import PageNotFound from "@/components2/page/PageNotFound.vue";

const routes = [
    {
        path: '/',
        name: 'index/',
        component: Posts,
        props: true
    },
    {
        path: '/index',
        name: 'index',
        component: Posts,
        props: true
    },
    {
        path: '/enter',
        name: 'enter',
        component: EnterPage,
    },
    {
        path: '/register',
        name: 'register',
        component: RegisterPage,
    },
    {
        path: '/profile/:id',
        name: 'profile',
        component: ProfilePage,
    },
    {
        path: '/post/:id',
        name: 'post-page',
        component: PostPage,
    },
    {
        path: '/add-post',
        name: 'add-post',
        component: AddPost,
    },
    {
        path: '/post',
        name: 'post',
        component: PostPage
    },
    {
        path: "/:pathMatch(.*)*",
        component: PageNotFound
    }
]
const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;