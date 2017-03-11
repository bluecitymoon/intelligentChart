(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('EducationLevelDeleteController',EducationLevelDeleteController);

    EducationLevelDeleteController.$inject = ['$uibModalInstance', 'entity', 'EducationLevel'];

    function EducationLevelDeleteController($uibModalInstance, entity, EducationLevel) {
        var vm = this;

        vm.educationLevel = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EducationLevel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
