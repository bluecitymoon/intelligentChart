(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFanSexDeleteController',PersonFanSexDeleteController);

    PersonFanSexDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonFanSex'];

    function PersonFanSexDeleteController($uibModalInstance, entity, PersonFanSex) {
        var vm = this;

        vm.personFanSex = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonFanSex.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
