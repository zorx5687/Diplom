<template>
    <main class="index-centre">
        <div class="middle">
            <router-view :item="item" :items="items" />
        </div>
    </main>
</template>


<script>
import Index from "@/components/page/Index.vue";

export default {
    props: ['items', 'item']
}
</script>

<style scoped>

</style>