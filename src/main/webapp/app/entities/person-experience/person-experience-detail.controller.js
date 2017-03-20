(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonExperienceDetailController', PersonExperienceDetailController);

    PersonExperienceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonExperience', 'Person'];

    function PersonExperienceDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonExperience, Person) {
        var vm = this;

        vm.personExperience = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personExperienceUpdate', function(event, result) {
            vm.personExperience = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
