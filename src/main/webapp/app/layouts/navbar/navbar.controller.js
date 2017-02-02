(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService', 'Menu', '$aside', '$scope', '$uibModalStack', 'MenuGroup'];

    function NavbarController ($state, Auth, Principal, ProfileService, LoginService, Menu, $aside, $scope, $uibModalStack, MenuGroup) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        $scope.menuGroups = [];

        loadAllMenus();

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;

        $scope.showSingleChart = showSingleChart;
        vm.showSideBar = showSideBar;
        vm.$state = $state;

        var asideInstance = null;
        //TODO show corresponding page for the url
        function showSingleChart(menu) {

            $uibModalStack.dismissAll();

            $state.go('chart-preview', {id: menu.id});
            //ui-sref="chart-preview/{{menu.id}}"
        }

        function showSideBar() {

             asideInstance = $aside.open({
                templateUrl: 'app/layouts/navbar/sidemenu.html',
                controller: 'NavbarController',
                placement: 'left',
                size: 'sm'
            });
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

            MenuGroup.groupsWithMenus().$promise.then(function (groups) {
                $scope.menuGroups = groups;
            });

            Menu.query({}, onSuccess, onError);

            function onSuccess(data) {
                $scope.menus = data;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
