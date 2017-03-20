(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonSocialHotDiscussDetailController', PersonSocialHotDiscussDetailController);

    PersonSocialHotDiscussDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonSocialHotDiscuss', 'Person'];

    function PersonSocialHotDiscussDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonSocialHotDiscuss, Person) {
        var vm = this;

        vm.personSocialHotDiscuss = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personSocialHotDiscussUpdate', function(event, result) {
            vm.personSocialHotDiscuss = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
