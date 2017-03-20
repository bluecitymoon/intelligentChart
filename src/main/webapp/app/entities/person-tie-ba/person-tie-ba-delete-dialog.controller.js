(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonTieBaDeleteController',PersonTieBaDeleteController);

    PersonTieBaDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonTieBa'];

    function PersonTieBaDeleteController($uibModalInstance, entity, PersonTieBa) {
        var vm = this;

        vm.personTieBa = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonTieBa.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
