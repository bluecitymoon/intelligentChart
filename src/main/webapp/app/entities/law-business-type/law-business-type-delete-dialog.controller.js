(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('LawBusinessTypeDeleteController',LawBusinessTypeDeleteController);

    LawBusinessTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'LawBusinessType'];

    function LawBusinessTypeDeleteController($uibModalInstance, entity, LawBusinessType) {
        var vm = this;

        vm.lawBusinessType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LawBusinessType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
