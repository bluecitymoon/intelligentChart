(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('FansPurchasingSectionDetailController', FansPurchasingSectionDetailController);

    FansPurchasingSectionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FansPurchasingSection'];

    function FansPurchasingSectionDetailController($scope, $rootScope, $stateParams, previousState, entity, FansPurchasingSection) {
        var vm = this;

        vm.fansPurchasingSection = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:fansPurchasingSectionUpdate', function(event, result) {
            vm.fansPurchasingSection = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
