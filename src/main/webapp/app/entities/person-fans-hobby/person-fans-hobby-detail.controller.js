(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFansHobbyDetailController', PersonFansHobbyDetailController);

    PersonFansHobbyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonFansHobby', 'Person', 'Hobby'];

    function PersonFansHobbyDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonFansHobby, Person, Hobby) {
        var vm = this;

        vm.personFansHobby = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personFansHobbyUpdate', function(event, result) {
            vm.personFansHobby = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
