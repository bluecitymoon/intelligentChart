(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonTieBaDetailController', PersonTieBaDetailController);

    PersonTieBaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonTieBa', 'Person'];

    function PersonTieBaDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonTieBa, Person) {
        var vm = this;

        vm.personTieBa = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personTieBaUpdate', function(event, result) {
            vm.personTieBa = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
