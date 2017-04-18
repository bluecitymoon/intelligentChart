(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('ProxyServerDeleteController',ProxyServerDeleteController);

    ProxyServerDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProxyServer'];

    function ProxyServerDeleteController($uibModalInstance, entity, ProxyServer) {
        var vm = this;

        vm.proxyServer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProxyServer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
