(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('WebsiteController', WebsiteController);

    WebsiteController.$inject = ['$scope', '$state', 'Website'];

    function WebsiteController ($scope, $state, Website) {
        var vm = this;

        vm.websites = [];

        loadAll();

        function loadAll() {
            Website.query(function(result) {
                vm.websites = result;
                vm.searchQuery = null;
            });
        }
    }
})();
