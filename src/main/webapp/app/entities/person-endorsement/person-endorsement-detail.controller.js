(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonEndorsementDetailController', PersonEndorsementDetailController);

    PersonEndorsementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonEndorsement', 'Person'];

    function PersonEndorsementDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonEndorsement, Person) {
        var vm = this;

        vm.personEndorsement = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personEndorsementUpdate', function(event, result) {
            vm.personEndorsement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
