(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('ConnectionLevelDeleteController',ConnectionLevelDeleteController);

    ConnectionLevelDeleteController.$inject = ['$uibModalInstance', 'entity', 'ConnectionLevel'];

    function ConnectionLevelDeleteController($uibModalInstance, entity, ConnectionLevel) {
        var vm = this;

        vm.connectionLevel = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ConnectionLevel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
