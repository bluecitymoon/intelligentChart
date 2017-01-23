(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService', 'Menu'];

    function NavbarController ($state, Auth, Principal, ProfileService, LoginService, Menu) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        loadAllMenus();

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;

        vm.showSingleChart = showSingleChart;
        vm.$state = $state;

        //TODO show corresponding page for the url
        function showSingleChart(menu) {

            collapseNavbar();

            $state.go('chart-preview', {id: menu.id});
            //ui-sref="chart-preview/{{menu.id}}"
        }
        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }

        function loadAllMenus () {

            Menu.query({}, onSuccess, onError);

            function onSuccess(data, headers) {
                // vm.links = ParseLinks.parse(headers('link'));
                // vm.totalItems = headers('X-Total-Count');
                // vm.queryCount = vm.totalItems;
                vm.menus = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
