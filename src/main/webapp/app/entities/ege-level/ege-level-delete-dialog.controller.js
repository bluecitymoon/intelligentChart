(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('EgeLevelDeleteController',EgeLevelDeleteController);

    EgeLevelDeleteController.$inject = ['$uibModalInstance', 'entity', 'EgeLevel'];

    function EgeLevelDeleteController($uibModalInstance, entity, EgeLevel) {
        var vm = this;

        vm.egeLevel = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EgeLevel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
