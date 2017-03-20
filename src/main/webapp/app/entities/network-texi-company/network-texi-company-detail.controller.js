(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('NetworkTexiCompanyDetailController', NetworkTexiCompanyDetailController);

    NetworkTexiCompanyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'NetworkTexiCompany'];

    function NetworkTexiCompanyDetailController($scope, $rootScope, $stateParams, previousState, entity, NetworkTexiCompany) {
        var vm = this;

        vm.networkTexiCompany = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:networkTexiCompanyUpdate', function(event, result) {
            vm.networkTexiCompany = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
