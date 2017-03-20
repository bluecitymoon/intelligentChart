(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonSocialMediaDetailController', PersonSocialMediaDetailController);

    PersonSocialMediaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PersonSocialMedia', 'Person', 'SocialMediaType', 'SocialMediaAttributeName'];

    function PersonSocialMediaDetailController($scope, $rootScope, $stateParams, previousState, entity, PersonSocialMedia, Person, SocialMediaType, SocialMediaAttributeName) {
        var vm = this;

        vm.personSocialMedia = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:personSocialMediaUpdate', function(event, result) {
            vm.personSocialMedia = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
