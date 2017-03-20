(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('SocialMediaTypeDetailController', SocialMediaTypeDetailController);

    SocialMediaTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SocialMediaType'];

    function SocialMediaTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, SocialMediaType) {
        var vm = this;

        vm.socialMediaType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:socialMediaTypeUpdate', function(event, result) {
            vm.socialMediaType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
