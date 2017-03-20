(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFansEducationLevelDetailController', PersonFansEducationLevelDetailController);

    PersonFansEducationLevelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonFansEducationLevel', 'Person', 'EducationLevel'];

    function PersonFansEducationLevelDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonFansEducationLevel, Person, EducationLevel) {
        var vm = this;

        vm.personFansEducationLevel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personFansEducationLevelUpdate', function(event, result) {
            vm.personFansEducationLevel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
