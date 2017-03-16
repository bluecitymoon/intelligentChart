(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonRelationDetailController', PersonRelationDetailController);

    PersonRelationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonRelation', 'Person'];

    function PersonRelationDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonRelation, Person) {
        var vm = this;

        vm.personRelation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personRelationUpdate', function(event, result) {
            vm.personRelation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
