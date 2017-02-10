(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MediaTypeDetailController', MediaTypeDetailController);

    MediaTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MediaType'];

    function MediaTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, MediaType) {
        var vm = this;

        vm.mediaType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:mediaTypeUpdate', function(event, result) {
            vm.mediaType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
