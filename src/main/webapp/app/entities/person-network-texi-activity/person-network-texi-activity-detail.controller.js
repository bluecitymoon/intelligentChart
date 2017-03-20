(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonNetworkTexiActivityDetailController', PersonNetworkTexiActivityDetailController);

    PersonNetworkTexiActivityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonNetworkTexiActivity', 'Person', 'NetworkTexiCompany'];

    function PersonNetworkTexiActivityDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonNetworkTexiActivity, Person, NetworkTexiCompany) {
        var vm = this;

        vm.personNetworkTexiActivity = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personNetworkTexiActivityUpdate', function(event, result) {
            vm.personNetworkTexiActivity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
