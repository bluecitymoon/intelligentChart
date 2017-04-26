(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('WebClientCookieDeleteController',WebClientCookieDeleteController);

    WebClientCookieDeleteController.$inject = ['$uibModalInstance', 'entity', 'WebClientCookie'];

    function WebClientCookieDeleteController($uibModalInstance, entity, WebClientCookie) {
        var vm = this;

        vm.webClientCookie = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WebClientCookie.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
