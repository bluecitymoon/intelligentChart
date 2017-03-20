(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFanSexDetailController', PersonFanSexDetailController);

    PersonFanSexDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonFanSex', 'Person', 'Sex'];

    function PersonFanSexDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonFanSex, Person, Sex) {
        var vm = this;

        vm.personFanSex = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personFanSexUpdate', function(event, result) {
            vm.personFanSex = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
