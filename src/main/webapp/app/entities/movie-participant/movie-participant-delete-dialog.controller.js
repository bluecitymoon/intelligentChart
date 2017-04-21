(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MovieParticipantDeleteController',MovieParticipantDeleteController);

    MovieParticipantDeleteController.$inject = ['$uibModalInstance', 'entity', 'MovieParticipant'];

    function MovieParticipantDeleteController($uibModalInstance, entity, MovieParticipant) {
        var vm = this;

        vm.movieParticipant = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MovieParticipant.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
