(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonEndorsementDeleteController',PersonEndorsementDeleteController);

    PersonEndorsementDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonEndorsement'];

    function PersonEndorsementDeleteController($uibModalInstance, entity, PersonEndorsement) {
        var vm = this;

        vm.personEndorsement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonEndorsement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
