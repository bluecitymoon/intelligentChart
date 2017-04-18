(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('ProxyServerDetailController', ProxyServerDetailController);

    ProxyServerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProxyServer'];

    function ProxyServerDetailController($scope, $rootScope, $stateParams, previousState, entity, ProxyServer) {
        var vm = this;

        vm.proxyServer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intelligentChartApp:proxyServerUpdate', function(event, result) {
            vm.proxyServer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
