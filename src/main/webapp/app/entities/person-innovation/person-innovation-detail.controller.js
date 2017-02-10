(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonInnovationDetailController', PersonInnovationDetailController);

    PersonInnovationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonInnovation', 'Person', 'InnovationType'];

    function PersonInnovationDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonInnovation, Person, InnovationType) {
        var vm = this;

        vm.personInnovation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personInnovationUpdate', function(event, result) {
            vm.personInnovation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
