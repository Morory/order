<template>
  <v-app>
    <v-navigation-drawer app permanent dark>
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="text-xl-h5">
            <div class="py-lg-3">
              수주관리시스템
            </div>
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-divider class="mx-2"/>

      <div v-if="currentUser = this.$store.state.auth.user">
        <v-menu absolute offset-y open-on-click>
          <template v-slot:activator="{ on }">
        <v-list-item two-line v-on="on">
          <v-list-item-avatar>
            <img src="./img/profile-image.jpg">
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title>{{currentUser.username}}</v-list-item-title>
            <v-list-item-subtitle>접속중</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
        <v-divider class="mx-2"/>
          </template>
          <v-list>
            <v-list-item @click.prevent="profile">
              <v-list-item-icon>
                <v-icon>mdi-account</v-icon>
              </v-list-item-icon>
              <v-list-item-title>프로필</v-list-item-title>
            </v-list-item>
            <v-list-item @click.prevent="logout">
              <v-list-item-icon>
                <v-icon>mdi-logout-variant</v-icon>
              </v-list-item-icon>
              <v-list-item-title>로그아웃</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </div>

      <v-list dense nav>
        <v-list-item
            v-for="item in items"
            :key="item.title"
            link
            :to="item.to"
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title class="text-xl-subtitle-1">{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-divider/>

        <v-list-item
            v-for="item in items2"
            :key="item.title"
            link
            :to="item.to"
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title class="text-xl-subtitle-1">{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

      <v-divider/>

      <v-list-item
          v-for="item in items3"
          :key="item.title"
          link
          :to="item.to"
      >
        <v-list-item-icon>
          <v-icon>{{ item.icon }}</v-icon>
        </v-list-item-icon>

        <v-list-item-content>
          <v-list-item-title class="text-xl-subtitle-1">{{ item.title }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-main class="overflow-hidden">
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: 'App',
  data: () => ({
    items: [
      { title: '홈', icon: 'mdi-home', to: '/'},
      { title: '견적서', icon: 'mdi-clipboard-list', to: '#1'},
      { title: '납품서', icon: 'mdi-truck-delivery', to: '#2'},
      { title: '청구서', icon: 'mdi-cash', to: '#3'},
      { title: '수주관리', icon: 'mdi-file-cog', to: '/order'},
      { title: '레포트', icon: 'mdi-file-chart', to: '#5'},
    ],
    items2: [
      { title: '수신함', icon: 'mdi-mailbox', to: '#6'}
    ],
    items3: [
      { title: '거래처', icon: 'mdi-folder-account', to: '/client'},
      { title: '품목관리', icon: 'mdi-database-cog', to: '#8'},
      { title: '이용내역', icon: 'mdi-history', to: '#9'},
      { title: '설정', icon: 'mdi-cog', to: '/setting'},
    ],
  }),

  methods: {
    logout() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    },
    profile() {
      this.$router.push('/profile');
    }
  },
};
</script>

<style>
html {
  overflow: auto;
}
</style>
