(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('FansPurchasingSectionController', FansPurchasingSectionController);

    FansPurchasingSectionController.$inject = ['$scope', '$stateParams', '$state', 'FansPurchasingSection'];

    function FansPurchasingSectionController ($scope, $stateParams, $state, FansPurchasingSection) {
        var vm = this;

        vm.fansPurchasingSections = [];

        loadAll();

        function loadAll() {
            FansPurchasingSection.query(function(result) {
                vm.fansPurchasingSections = result;
                vm.searchQuery = null;
            });
        }
    }
})();
