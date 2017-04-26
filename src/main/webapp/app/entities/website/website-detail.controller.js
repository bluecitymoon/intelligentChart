(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('WebsiteDetailController', WebsiteDetailController);

    WebsiteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Website'];

    function WebsiteDetailController($scope, $rootScope, $stateParams, previousState, entity, Website) {
        var vm = this;

        vm.website = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:websiteUpdate', function(event, result) {
            vm.website = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
