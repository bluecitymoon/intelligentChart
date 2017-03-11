(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('EducationLevelDetailController', EducationLevelDetailController);

    EducationLevelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EducationLevel'];

    function EducationLevelDetailController($scope, $rootScope, $stateParams, previousState, entity, EducationLevel) {
        var vm = this;

        vm.educationLevel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:educationLevelUpdate', function(event, result) {
            vm.educationLevel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
