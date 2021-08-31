/* Main application script */
Vue.config.productionTip = false;

let bus = new Vue();

bus.EVENT_UPDATE_CURRENT_USER = 'update-current-user';
bus.EVENT_USER_LOGIN = 'user-login';
bus.EVENT_USER_LOGOUT = 'user-logout';

Vue.component("goods-list", {

    template: "#templateGoodsList",

    data: function () {
        return {
            items: []
        };
    },

    created: function () {
    },

    mounted: function () {
        let self = this;

        axios.post("/goods/get-list")
            .then(function (answer) {

                self.items = answer.data;
            });
    },
    methods: {}
});

Vue.component("goods-item", {

    template: "#templateGoodItem",

    props: ['item'],

    data: function () {
        return {};
    },

    created: function () {
    },

    mounted: function () {
    },

    methods: {}
})

Vue.component("buttons-panel", {

    template: "#templateButtonsPanel",

    data: function () {
        return {showIt: false};
    },

    created: function () {
        let self = this;

        bus.$on(bus.EVENT_USER_LOGIN, function () {
            self.showIt = true
        });
        bus.$on(bus.EVENT_USER_LOGOUT, function () {
            self.showIt = false
        });
    },

    mounted: function () {
    },

    methods: {

        onLoginClick: function () {
            app.showBlock(app.blocks.LOGIN_FORM);
        },

        onLogoutClick: function () {
            axios.post("/logout")
                .then(function (answer) {
                    bus.$emit(bus.EVENT_UPDATE_CURRENT_USER);
                });
        },

        onRegistrationClick: function () {
            app.showBlock(app.blocks.REGISTER_FORM);
        }
    }
});

Vue.component("buttons-admin-panel", {

    template: "#templateButtonsAdminPanel",

    data: function () {
        return {showIt: false};
    },

    created: function () {
        let self = this;

        bus.$on(bus.EVENT_USER_LOGIN, function () {
            self.showIt = app.currentUserIsAdmin;
        });
        bus.$on(bus.EVENT_USER_LOGOUT, function () {
            self.showIt = app.currentUserIsAdmin;
        });
    },

    mounted: function () {
    },

    methods: {
        onClickGoodsIcon: function () {
            app.showBlock(app.blocks.GOODS_LIST);
        }
    }
});

Vue.component("login-form", {

    template: "#templateLoginForm",

    data: function () {
        return {
            password: "",
            username: ""
        };
    },

    methods: {
        loginDo: function () {

            var params = new URLSearchParams();

            params.append('username', this.username);
            params.append('password', this.password);

            var config = {
                headers: {
                    'Content-type': 'application/x-www-form-urlencoded'
                }
            };

            axios.post("/authorize", params, config)
                .then(function (answer) {
                    bus.$emit(bus.EVENT_UPDATE_CURRENT_USER);
                });

        }
    }
});

Vue.component("register-form", {

    template: "#templateRegisterForm",

    data: function () {
        return {
            username: "",
            password: ""
        }
    },

    created: function () {

    },

    methods: {
        registerDo: function () {

            axios.post("/register-user", {
                username: this.username,
                password: this.password
            }).then(function (answer) {
                //@todo bootstrap notification
                app.showBlock(app.blocks.NONE);
                alert(answer.data);
            });
        }
    }
});

let app = new Vue({

    el: "#vueapp",

    created: function () {

        bus.$on(bus.EVENT_UPDATE_CURRENT_USER, this.updateCurrentUser);
    },

    data: {
        currentBlock: null,
        blocks: {
            NONE: 0,
            LOGIN_FORM: 1,
            REGISTER_FORM: 2,
            GOODS_LIST: 3,
        },
        currentUser: null,
        currentUserIsAdmin: false,
    },

    methods: {

        showBlock: function (blockId) {
            this.currentBlock = blockId;
        },

        isShowedBlock: function (blockId) {
            return this.currentBlock === blockId;
        },

        updateCurrentUser: function () {
            let self = this;

            axios.post("/get-current-user")
                .then(function (answer) {
                    self.currentUser = answer.data;

                    if (self.currentUser.id) {

                        axios.post('/is-current-user-admin')
                            .then(function (answer) {
                                self.currentUserIsAdmin = answer.data === true;
                                bus.$emit(bus.EVENT_USER_LOGIN);
                            });

                        alert("Авторизация успешна:" + self.currentUser.username);
                        app.showBlock(app.blocks.NONE);
                    } else {
                        bus.$emit(bus.EVENT_USER_LOGOUT);
                        alert("Пользователь вышел из системы.");
                        app.showBlock(app.blocks.NONE);
                    }
                });
        }
    }
});