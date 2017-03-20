(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('EducationBackgroundTypeDetailController', EducationBackgroundTypeDetailController);

    EducationBackgroundTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EducationBackgroundType'];

    function EducationBackgroundTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, EducationBackgroundType) {
        var vm = this;

        vm.educationBackgroundType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:educationBackgroundTypeUpdate', function(event, result) {
            vm.educationBackgroundType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
