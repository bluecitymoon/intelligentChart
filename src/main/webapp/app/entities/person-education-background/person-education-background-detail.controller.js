(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonEducationBackgroundDetailController', PersonEducationBackgroundDetailController);

    PersonEducationBackgroundDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonEducationBackground', 'EducationBackgroundType', 'Person'];

    function PersonEducationBackgroundDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonEducationBackground, EducationBackgroundType, Person) {
        var vm = this;

        vm.personEducationBackground = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personEducationBackgroundUpdate', function(event, result) {
            vm.personEducationBackground = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
