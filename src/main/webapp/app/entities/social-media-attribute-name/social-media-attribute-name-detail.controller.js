(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('SocialMediaAttributeNameDetailController', SocialMediaAttributeNameDetailController);

    SocialMediaAttributeNameDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SocialMediaAttributeName'];

    function SocialMediaAttributeNameDetailController($scope, $rootScope, $stateParams, previousState, entity, SocialMediaAttributeName) {
        var vm = this;

        vm.socialMediaAttributeName = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:socialMediaAttributeNameUpdate', function(event, result) {
            vm.socialMediaAttributeName = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
