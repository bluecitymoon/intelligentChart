(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFansPucharsingPowerDetailController', PersonFansPucharsingPowerDetailController);

    PersonFansPucharsingPowerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonFansPucharsingPower', 'Person', 'FansPurchasingSection'];

    function PersonFansPucharsingPowerDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonFansPucharsingPower, Person, FansPurchasingSection) {
        var vm = this;

        vm.personFansPucharsingPower = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personFansPucharsingPowerUpdate', function(event, result) {
            vm.personFansPucharsingPower = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
