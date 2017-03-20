(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonEducationBackgroundDeleteController',PersonEducationBackgroundDeleteController);

    PersonEducationBackgroundDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonEducationBackground'];

    function PersonEducationBackgroundDeleteController($uibModalInstance, entity, PersonEducationBackground) {
        var vm = this;

        vm.personEducationBackground = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonEducationBackground.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
