(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('WebClientCookieDetailController', WebClientCookieDetailController);

    WebClientCookieDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WebClientCookie', 'Website', 'ProxyServer'];

    function WebClientCookieDetailController($scope, $rootScope, $stateParams, previousState, entity, WebClientCookie, Website, ProxyServer) {
        var vm = this;

        vm.webClientCookie = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:webClientCookieUpdate', function(event, result) {
            vm.webClientCookie = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
