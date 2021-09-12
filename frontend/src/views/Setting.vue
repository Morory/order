<template>
  <v-card
      dark
      flat
  >
    <v-toolbar
        flat
        height="72"
    >
      <v-switch
          v-model="$vuetify.theme.dark"
          inset
          label="ダーク·モード"
          persistent-hint
      ></v-switch>
    </v-toolbar>
  </v-card>
</template>

<script>
export default {
  name: "Setting"
}
</script>

<style scoped>

</style>