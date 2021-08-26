/* Main application script */
let bus = new Vue();

bus.EVENT_UPDATE_CURRENT_USER = 'update-current-user';
bus.EVENT_USER_LOGIN = 'user-login';
bus.EVENT_USER_LOGOUT = 'user-logout';

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
            app.loginFormShow = true;
            app.registerFormShow = false;
        },

        onLogoutClick: function () {
            axios.post("/logout")
                .then(function (answer) {
                    bus.$emit(bus.EVENT_UPDATE_CURRENT_USER);
                });
        },

        onRegistrationClick: function () {
            app.registerFormShow = true;
            app.loginFormShow = false;
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
                alert(answer.data);
                app.registerFormShow = false;
                app.loginFormShow = true;
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
        loginFormShow: false,
        registerFormShow: false,
        currentUser: null
    },

    methods: {
        updateCurrentUser: function () {
            let self = this;

            axios.post("/get-current-user")
                .then(function (answer) {
                    self.currentUser = answer.data;

                    if (self.currentUser.id) {
                        bus.$emit(bus.EVENT_USER_LOGIN);
                    } else {
                        bus.$emit(bus.EVENT_USER_LOGOUT);
                    }
                });
        }
    }
});